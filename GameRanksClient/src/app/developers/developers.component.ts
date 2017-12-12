import { Component, OnInit } from '@angular/core';

import {DeveloperStruct} from "../models/serverStructs/element/DeveloperStruct";
import {DALForReadsService} from "../DAL/DALForReads.service";
import {ResponseToModelService} from "../ResponseToModel.service";

@Component({
  selector: 'app-developers',
  templateUrl: './developers.component.html',
  styleUrls: ['./developers.component.css']
})
export class DevelopersComponent implements OnInit {
	topThree: DeveloperStruct[];

	developers: DeveloperStruct[];
	endOfList = 0;
	toGet = 10;
	allLoaded = false;

	filterOpen = false;
	filterValues = {
		name: "",
		order: "Decreasing"
	};

	elements = {
		filterOpener: null,
		filter: null,
		filterName: null,
		filterOrder: null
	};

	constructor(private DALForReads: DALForReadsService, private responseToModelService: ResponseToModelService) {

	}

	async ngOnInit() {
		window.scrollTo(0, 0);

		await this.DALForReads.getDevelopersPageData().subscribe(function(result){
			this.developers = this.responseToModelService.convertDevelopers(result);
			this.endOfList = this.developers.length;

			//Ha kevesebb jott, mint kertunk, akkor tobbet mar nem fogunk kerni
			if(this.developers.length != this.toGet){
				this.allLoaded = true;
			}

			//A top harmat kulon vesszuk, mivel a games valtozik, ha szurunk
			this.topThree = this.developers.slice(0, 3);
		}.bind(this));

		this.elements.filterOpener = document.getElementById("listFilterOpener");
		this.elements.filter = document.getElementById("listFilter");
		this.elements.filterName = document.getElementById("filterName");
		this.elements.filterOrder = document.getElementById("filterOrder");

		document.getElementById("list").addEventListener("scroll", this.listAtBottomListener.bind(this));
	}

	filterOpenerClicked(){
		if(!this.filterOpen){
			this.filterOpen = true;
			this.elements.filterOpener.classList.add("listFilterOpenerOn");
			this.elements.filter.className = "openListFilter";
		}
		else{
			this.filterOpen = false;
			this.elements.filterOpener.classList.remove("listFilterOpenerOn");
			this.elements.filter.className = "closeListFilter";
		}
	}

	filter(){
		var newName = this.elements.filterName.value.toLowerCase(),
			newOrder = this.elements.filterOrder.options[this.elements.filterOrder.selectedIndex].text;

		//Ha barmelyik ertek elter az elozo ertektol, akkor szurunk
		if(newName != this.filterValues.name || newOrder != this.filterValues.order){
			this.filterValues.name = newName;
			this.filterValues.order = newOrder;

			this.endOfList = 0;
			this.allLoaded = false;

			this.loadDevelopers(true);
		}
	}

	listAtBottomListener(event){
		var element = event.target;
		if (element.scrollHeight - element.scrollTop === element.clientHeight){
			this.loadDevelopers(false);
		}
	}

	loadDevelopers(withFilter){
		if(!this.allLoaded){
			this.DALForReads.getFilteredDevelopers(this.endOfList, this.toGet, this.filterValues.name, this.filterValues.order === "Ascending").subscribe(function(newDevelopers){
				newDevelopers = this.responseToModelService.convertDevelopers(newDevelopers);
				this.developers = withFilter ? newDevelopers : this.developers.concat(newDevelopers);
				this.endOfList = this.developers.length;

				//Ha kevesebb jott, mint kertunk, akkor tobbet mar nem fogunk kerni
				if(newDevelopers.length != this.toGet){
					this.allLoaded = true;
				}
			}.bind(this));
		}
	}
}
