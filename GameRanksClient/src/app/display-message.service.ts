import { Injectable } from '@angular/core';

@Injectable()
export class DisplayMessageService {
	first = true;

	elements = {
		messageC: document.getElementById("messageC"),
		message: document.getElementById("message")
	};

	constructor() { }

	init(){
		this.elements.messageC = document.getElementById("messageC");
		this.elements.message = document.getElementById("message");
	}

	displayMessage(message, isError){
		if(this.first){
			this.first = false;
			this.init();
		}

		var messageType = "",
			timeout = 0;

		if(isError != null){
			messageType = isError ? "error" : "success";
			timeout = isError ? 5000 : 3000;
		}
		else{
			messageType = "neutral";
			timeout = 5000;
		}

		this.elements.message.innerHTML = message;
		this.elements.messageC.classList.add(messageType);
		this.elements.messageC.classList.add("displayMessage");

		setTimeout(function(){
			this.elements.messageC.className = "";
		}.bind(this), timeout);
	}
}
