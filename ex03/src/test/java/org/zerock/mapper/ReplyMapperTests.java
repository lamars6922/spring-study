package org.zerock.mapper;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
//Java Config
//@ContextConfiguration(classes={org.zerock.config.RootConfig.class})
@Log4j
public class ReplyMapperTests {
	@Setter(onMethod_ = {@Autowired})
	private ReplyMapper mapper;
	
	//테스트 전에 해당 번호 게시물이 존재하는지 꼭 확인
	private Long[] bnoArr = {4718707L, 4718705L, 4718704L, 4718703L, 4718702L };

	@Test
	public void testCreate() {
		IntStream.rangeClosed(1,  10).forEach(i->{
			ReplyVO vo = new ReplyVO();
			
			vo.setBno(bnoArr[i%5]);
			vo.setReply("댓글 테스트 " + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);
		});
	}
	
//	@Test
//	public void testMapper( ) {
//		log.info(mapper);
//	}
}