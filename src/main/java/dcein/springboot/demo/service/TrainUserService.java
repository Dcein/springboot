package dcein.springboot.demo.service;

import dcein.springboot.demo.mybatis.dao.TrainUserMapper;
import dcein.springboot.demo.mybatis.entity.TrainUser;
import dcein.springboot.demo.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: DingCong
 * @Description: 用户相关service
 * @@Date: Created in 19:52 2018/7/12
 */
@Service
public class TrainUserService {

    @Autowired
    private TrainUserMapper trainUserMapper ;

    public boolean userLoginCheck(TrainUser trainUser){
        boolean state = false ;
        TrainUser user = trainUserMapper.selectOne(trainUser);
        if (!CommonUtils.isNullOrEmptyOfObj(user)){
            state = true ;
        }
        return state ;
    }
}
