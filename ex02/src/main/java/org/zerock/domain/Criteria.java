package org.zerock.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {

	private int pageNum;
    private int amount;
    
    public Criteria(){
    this(1,10); // 1 페이지, 10개 게시글
    }
    
    public Criteria(int pageNum, int amount) {
    	this.pageNum = pageNum;
        this.amount = amount;
        }
 }