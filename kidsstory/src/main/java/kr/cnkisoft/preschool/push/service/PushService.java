package kr.cnkisoft.preschool.push.service;

import kr.cnkisoft.preschool.push.domain.PreSchoolPushIdDto;

/**
 * Created by PureMaN on 2017-08-27.
 */
public interface PushService {
	public void sendPush(String deviceId, String Message, String uri);
	public void registPushId(String contact, String deviceId, String osType);
}
