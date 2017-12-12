import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import {DALForReadsService} from "../DAL/DALForReads.service";
import {ResponseToModelService} from "../ResponseToModel.service";
import {MainPageStruct} from "../models/serverStructs/page/MainPageStruct";



@Component({
	selector: 'app-home',
	templateUrl: './home.component.html',
	styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
	mainPageStruct: MainPageStruct = new MainPageStruct([], [], []);
	currentCategoryElement = null;
	categoryToShow = "g";

	constructor(private http: HttpClient, private DALForReads: DALForReadsService, private responseToModelService: ResponseToModelService) {

	}

	async ngOnInit() {
		window.scrollTo(0, 0);

		await this.DALForReads.getHomePageData().subscribe(function(result){
			this.mainPageStruct = this.responseToModelService.convertMainPageStruct(result);
		}.bind(this));

		this.currentCategoryElement = document.getElementById("topThreeCategorySelected");
	}

	categoryClicked($event, label){
		this.currentCategoryElement.id = "";
		$event.currentTarget.id = "topThreeCategorySelected";
		this.currentCategoryElement = $event.currentTarget;
		this.categoryToShow = label;
	}
}
