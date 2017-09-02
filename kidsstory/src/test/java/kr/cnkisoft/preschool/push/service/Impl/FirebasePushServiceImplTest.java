package kr.cnkisoft.preschool.push.service.Impl;

import kr.cnkisoft.KidsstoryApplication;
import kr.cnkisoft.preschool.push.service.PushService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by PureMaN on 2017-08-27.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(KidsstoryApplication.class)
@RunWith(SpringRunner.class)
public class FirebasePushServiceImplTest {

	@Autowired
	PushService pushService;

	@Test
	public void send() {
		pushService.sendPush("APA91bGL4JHuEnLxEw_LL5nBSF5gRHmQlT2DR5VQgwLHpH4JoYCODLHyXpP7_3rRMYHh1csefa9x3SxAqiVTjAdXI51TL8JwYQe_z39_5ckEZhdvqJ_BM4SiCx_lZAL3W0CEoBE6Fea_", "test", "http://cnkisoft.cafe24.com/board/parent/busline");
	}

}