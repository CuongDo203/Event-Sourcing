package com.microservice.bookservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.bookservice.command.data.Book;
import com.microservice.bookservice.command.data.BookRepository;
import com.microservice.bookservice.query.model.BookResponseModel;
import com.microservice.bookservice.query.queries.GetAllBookQuery;
import com.microservice.commonservice.model.BookResponseCommonModel;
import com.microservice.commonservice.queries.GetBookDetailQuery;

@Component
public class BookProjection {

	@Autowired
	private BookRepository bookRepository;
	
	@QueryHandler
	public List<BookResponseModel> handle(GetAllBookQuery query) {
		List<Book> list = bookRepository.findAll();
		List<BookResponseModel> res = new ArrayList<>();
		list.forEach(book -> {
			BookResponseModel model = new BookResponseModel();
			BeanUtils.copyProperties(book, model);
			res.add(model);
		});
		return res;
	}
	
	@QueryHandler
	public BookResponseCommonModel handle(GetBookDetailQuery query) throws Exception {
		BookResponseCommonModel model = new BookResponseCommonModel();
		
		Book book = bookRepository.findById(query.getId()).orElseThrow(()-> 
			new Exception("Not found with id: "+ query.getId())
		);
		
		BeanUtils.copyProperties(book, model);
		return model;
	}
}
