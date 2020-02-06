package com.javainuse.controller;

import com.javainuse.model.FileUtils;
import com.javainuse.model.MonitoredData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javainuse.model.Patient;
import com.javainuse.service.RabbitMQSender;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/javainuse-rabbitmq/")
public class RabbitMQWebController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	@Autowired
	FileUtils fileUtils;


	@GetMapping(value = "/producer")
	public String producer() throws InterruptedException, IOException {

		List<MonitoredData> monitoredDatas = fileUtils.citesteFisier();
		int[] pacientsIds=new int[]{15,16,17};
		for(MonitoredData m:monitoredDatas) {
			Patient e=new Patient();
			e.setIdPacient(String.valueOf(pacientsIds[new Random().nextInt(pacientsIds.length)]));
			e.setStartTime(m.getStartTime().toString().replace('T',' '));
			e.setEndTime(m.getEndTime().toString().replace('T',' '));
			e.setActivity(m.getActivity());
			rabbitMQSender.send(e);
			Thread.sleep(2000);
		}
		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}

}
