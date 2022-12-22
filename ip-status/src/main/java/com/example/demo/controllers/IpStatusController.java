package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Device;
import com.example.demo.repositories.DevicesRepo;
import com.example.demo.services.IpStatusChecker;

@CrossOrigin
@RestController
public class IpStatusController {

    @Autowired
    private IpStatusChecker statusCheckerService;

    @Autowired
    private DevicesRepo deviceRepo;

    @GetMapping("/")
    public String root() {
        return "Hello World";
    }

    @GetMapping("/ip/{ip}")
    public boolean getMethodName(@PathVariable("ip") String ip) {
        final int TIME_LATENCY = 2000;
        final int status = this.statusCheckerService.getIpStatus(ip);
        try {
			Thread.sleep(TIME_LATENCY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        return status == 1 ? true : false;
    }

    @GetMapping("/devices")
    public ArrayList<Device> getDeviceList() {
        return deviceRepo.getDeviceList();
    }

    // adding device
    @PostMapping("/add-device")
    public Device addDevice(@RequestBody HashMap<String, String> body) {
        
        if(body.containsKey("ip")) {
            String ip = body.get("ip");
            String name = body.get("name");
            
            final Device device = deviceRepo.addDevice(ip, name);

            return device;
        }

        return null;
    }

    @PostMapping("/import")
    public ArrayList<Device> importDevices(@RequestBody HashMap<String, HashMap<String, String>[]> body) {
        final String DEVICE_LIST = "deviceList";
        if(body.get(DEVICE_LIST) != null) {
            HashMap<String, String>[] hashMap = body.get(DEVICE_LIST);
            deviceRepo.updateDeviceList(hashMap);
        }
        return deviceRepo.getDeviceList();
    }

    @PostMapping("/delete")
    public ArrayList<Device> deleteDevice(@RequestBody HashMap<String, String> body) {
        final String DEVICE_ID = "deviceId";
        if(body.containsKey(DEVICE_ID)) {
            String deviceId = body.get(DEVICE_ID);

            deviceRepo.deleteDevice(deviceId);
        }
        return deviceRepo.getDeviceList();
    }
}
