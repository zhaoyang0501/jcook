package com.pzy.jcook.workflow.service.nofy;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
/***
 * 阿里大于实现的短息发送
 * @author panchaoyang
 *
 */
@Service
public class DayuSMSNofyServiceImpl implements NofyService{
	
	@Value("${spring.dayusms.url}")
	private String url;
	
	@Value("${spring.dayusms.appkey}")
	private String appkey;
	
	@Value("${spring.dayusms.secret}")
	private String secret;
	
	@Value("${spring.dayusms.signName}")
	private String signName;
	
	@Value("${spring.dayusms.templateCode}")
	private String templateCode;
	
	@Value("${spring.dayusms.isSend}")
	private Boolean isSend;
	
	private static final Logger log = LoggerFactory.getLogger(DayuSMSNofyServiceImpl.class);
	
	@Override
	@Async
	public void send(Map<String,Object> map)  {
		if(!isSend)
			return ;
		log.info("使用阿里大于短息接口发送一条短息");
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName("任务分配系统");
		req.setRecNum((String)map.get("phone"));
		req.setSmsTemplateCode( templateCode );
		AlibabaAliqinFcSmsNumSendResponse rsp =null;
		try {
			req.setSmsParamString( buildParamString(map) );
			rsp = client.execute(req);
		} catch (ApiException e) {
			log.error("使用阿里大于发送短息发送故障故障代码{}",e.getErrCode());
		} catch (JsonProcessingException e) {
			log.error("使用阿里大于发送短息发送故障,json转换故障{}",e);
		}
		System.out.println(rsp.getBody());
	}
	
	private String buildParamString(Map<String,Object> map) throws JsonProcessingException{
		Map<String,Object> newmap = new HashMap<String,Object>();
		newmap.put("username", map.get("username"));
		newmap.put("taskname", map.get("taskname"));
		ObjectMapper mapper = new ObjectMapper(); 
		String paramstr = mapper.writeValueAsString(newmap);
		return paramstr;
	}
}
