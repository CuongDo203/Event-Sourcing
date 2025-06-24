package com.microservice.borrowingservice.command.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.microservice.borrowingservice.command.command.DeleteBorrowingCommand;
import com.microservice.borrowingservice.command.event.BorrowingCreatedEvent;
import com.microservice.borrowingservice.command.event.BorrowingDeletedEvent;
import com.microservice.commonservice.command.RollbackStatusBookCommand;
import com.microservice.commonservice.command.UpdateStatusBookCommand;
import com.microservice.commonservice.event.BookRollbackStatusEvent;
import com.microservice.commonservice.event.BookUpdatedStatusEvent;
import com.microservice.commonservice.model.BookResponseCommonModel;
import com.microservice.commonservice.model.EmployeeResponseCommonModel;
import com.microservice.commonservice.queries.GetBookDetailQuery;
import com.microservice.commonservice.queries.GetDetailEmployeeQuery;

import lombok.extern.slf4j.Slf4j;

@Saga
@Slf4j
public class BorrowingSaga {

	@Autowired
	private transient CommandGateway commandGateway;
	
	@Autowired
	private transient QueryGateway queryGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty = "id")
	private void handle(BorrowingCreatedEvent event) {
		log.info("BorrowingCreatedEvent in saga for bookid: "+ event.getBookId()+
				" Employee id: "+ event.getEmployeeId());
		try {
			//kiem tra quyen sach co nguoi muon hay chua
			GetBookDetailQuery getBookDetailQuery = new GetBookDetailQuery(event.getBookId());
			BookResponseCommonModel bookResponseCommonModel = queryGateway.query(getBookDetailQuery, 
					ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
			if(!bookResponseCommonModel.isReady()) {
				throw new Exception("Sach da co nguoi muon");
			}
			else {
				SagaLifecycle.associateWith("bookId", event.getBookId());
				UpdateStatusBookCommand command = new UpdateStatusBookCommand(
						event.getBookId(), false, event.getEmployeeId(), event.getId());
				commandGateway.sendAndWait(command);
			}
		}
		catch(Exception e) {
			rollbackBorrowingRecord(event.getId());
			log.error(e.getMessage());
		}
	}
	
	//Sau khi update xong status sach
	@SagaEventHandler(associationProperty = "bookId")
	private void handler(BookUpdatedStatusEvent event) {
		log.info("BookUpdateStatusEvent in Saga for Bookid: "+ event.getBookId());
		try {
			//get thong tin cua nhan vien
			GetDetailEmployeeQuery query = new GetDetailEmployeeQuery(event.getEmployeeId());
			EmployeeResponseCommonModel employeeModel = queryGateway.query(query, 
					ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
			if(employeeModel.getIsDisciplined()) {
				throw new Exception("Nhan vien da bi ky luat");
			}
			else {
				log.info("Da muon sach thanh cong");
				SagaLifecycle.end();
			}
		}
		catch(Exception e) {
			rollbackBookStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowingId());
			log.error(e.getMessage());
		}
	}
	
	private void rollbackBorrowingRecord(String id) {
		DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
		commandGateway.sendAndWait(command);
		
	}
	
	private void rollbackBookStatus(String bookId, String employeeId, String borrowingId) {
		SagaLifecycle.associateWith("bookId", bookId);
		RollbackStatusBookCommand command = new RollbackStatusBookCommand(bookId,
				true, employeeId, borrowingId
				);
		commandGateway.sendAndWait(command);
	}
	
	@SagaEventHandler(associationProperty = "bookId")
	private void handler(BookRollbackStatusEvent event) {
		log.info("BookRollbackStatusEvent in Saga for BookId {}: "+event.getBookId());
		rollbackBorrowingRecord(event.getBorrowingId());
	}
	
	@SagaEventHandler(associationProperty = "id")
	private void handle(BorrowingDeletedEvent event) {
		log.info("BorrowDeletedEvent in Saga for borrowingid {}: "+event.getId());
		SagaLifecycle.end();
	}
}
