package com.example.demo.models;

import java.util.UUID;

public class Device {
    private String ip;
    private String name;
    private int status;
    private String id;

    
	public Device(String ip, String name, int status) {
		this.ip = ip;
		this.name = name;
		this.status = status;
        this.id = generateId();
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

    private String generateId() {
        return UUID.randomUUID().toString();
    }
    
}
