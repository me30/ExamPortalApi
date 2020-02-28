package com.nx.service;

import com.nx.entity.Result;

public interface ResultService extends IFinder<Result>, IService<Result>{

	Result getResultByExamIdandUserId(Long exam_id,Long user_id);
	
}
