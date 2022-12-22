package com.example.demo.timer_tasks;

import java.util.ArrayList;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.demo.models.Device;
import com.example.demo.repositories.DevicesRepo;
import com.example.demo.services.IpStatusChecker;
import com.example.demo.socket.WebSocketController;
 
public class PeriodicIpStatusChecker extends TimerTask{

	@Autowired
	private DevicesRepo devicesRepo = new DevicesRepo();

	@Autowired
	private IpStatusChecker ipStatusCheckerService = new IpStatusChecker();

	@Autowired
	private WebSocketController webSocketController = new WebSocketController();

	@Override
	public void run() {
		startIpStatusChecker();
	}

	public void startIpStatusChecker() {
		// first take a copy of deviceList
		// because while checking ipstatus of the entire list if user deletes any device,
		// in foreach loop we will be in trouble

		ArrayList<Device> deviceList = devicesRepo.getDeviceList();
		ArrayList<Device> deviceListCopy = new ArrayList<>();

		for(Device device : deviceList) {
			deviceListCopy.add(device);
		}

		updateAllDevicesIpStatus(deviceListCopy);
	}

	public void updateAllDevicesIpStatus(ArrayList<Device> deviceList) {

		// going thrugh each device & updating the status
		for(Device device : deviceList) {
			String ip = device.getIp();
			int ipStatus = ipStatusCheckerService.getIpStatus(ip);
			// updating the status
			device.setStatus(ipStatus);
		}
	}
}
