package kr.cnkisoft.preschool.push.service.Impl;

import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;
import kr.cnkisoft.preschool.push.mapper.PushMapper;
import kr.cnkisoft.preschool.push.service.PushService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;


/**
 * Created by PureMaN on 2017-08-27.
 */
@Service
@Slf4j
public class FirebasePushServiceImpl implements PushService{

	private static final String KIDSSTORY_FCM_SERVER_KEY = "AAAADH-BVz8:APA91bExiS28nJMiRa5d0_ynjS0ClKKIXD6gT3zTG6CMAC3YSluoPWlZO_IN0yBhB8BBgE3WJVp2RVjzpze59vG1lrC1Eqs4-kp8Y49fnN4ZwT5FtR4VeZIN1gBwaNMakssZc4n7chRb";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

//	private static final String FIREBASE_API_URL = "https://gcm-http.googleapis.com/gcm/send";

	@Autowired
	PushMapper pushMapper;

	@Override
	public void sendPush(String deviceId, String Message, String uri) {
		JSONObject body = new JSONObject();
		
		try {
			body.put("to", deviceId);
			
			JSONObject data = new JSONObject();
			data.put("text", Message);
			data.put("url", uri);
			
			body.put("data", data);
		} catch (Exception e) {
			log.error("JSON create error");
		}

		log.info(body.toString());

		HttpHeaders header = new HttpHeaders();
		Charset utf8 = Charset.forName("UTF-8");
		MediaType mediaType = new MediaType("application", "json", utf8);
		header.setContentType(mediaType);
		header.add("Authorization", "key=" + KIDSSTORY_FCM_SERVER_KEY);

		HttpEntity<String> request = new HttpEntity<>(body.toString(), header);

		String pushNotification = send(request);

		log.info(pushNotification);
	}

	@Override
	public void registPushId(String contact, String deviceId, String osType) {
		PreSchoolPushIdDto preSchoolPushId = new PreSchoolPushIdDto();
		preSchoolPushId.setContact(contact);
		preSchoolPushId.setDeviceId(deviceId);
		preSchoolPushId.setOsType(osType);

		pushMapper.insertPushId(preSchoolPushId);
	}


	private String send(HttpEntity<String> entity) {
		RestTemplate restTemplate = new RestTemplate();

		/**
		 https://fcm.googleapis.com/fcm/send
		 Content-Type:application/json
		 Authorization:key=FIREBASE_SERVER_KEY*/

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return firebaseResponse;
	}
}
