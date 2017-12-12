import { Component, OnInit } from '@angular/core';
import {GameStruct} from "../models/serverStructs/element/GameStruct";
import {DALForReadsService} from "../DAL/DALForReads.service";
import {ResponseToModelService} from "../ResponseToModel.service";

@Component({
	selector: 'app-games',
	templateUrl: './games.component.html',
	styleUrls: ['./games.component.css']
})
export class GamesComponent implements OnInit {
	topThree: GameStruct[] = [];

	games: GameStruct[] = [];
	endOfList = 0;
	toGet = 10;
	allLoaded = false;

	filterOpen = false;
	filterValues = {
		name: "",
		platform: "All",
		genre: "All",
		order: "Decreasing"
	};

	elements = {
		filterOpener: null,
		filter: null,
		filterName: null,
		filterPlatform: null,
		filterGenre: null,
		filterOrder: null
	};

	constructor(private DALForReads: DALForReadsService, private responseToModelService: ResponseToModelService) {

	}

	async ngOnInit() {
		window.scrollTo(0, 0);

		await this.DALForReads.getGamesPageData().subscribe(function(result){
			this.games = this.responseToModelService.convertGames(result);
			this.endOfList = this.games.length;

			//A top harmat kulon vesszuk, mivel a games valtozik, ha szurunk
			this.topThree = this.games.slice(0, 3);
		}.bind(this));

		this.elements.filterOpener = document.getElementById("listFilterOpener");
		this.elements.filter = document.getElementById("listFilter");
		this.elements.filterName = document.getElementById("filterName");
		this.elements.filterPlatform = document.getElementById("filterPlatform");
		this.elements.filterGenre = document.getElementById("filterGenre");
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
			newPlatform = this.elements.filterPlatform.options[this.elements.filterPlatform.selectedIndex].text,
			newGenre = this.elements.filterGenre.options[this.elements.filterGenre.selectedIndex].text,
			newOrder = this.elements.filterOrder.options[this.elements.filterOrder.selectedIndex].text;

		//Ha barmelyik ertek elter az elozo ertektol, akkor szurunk
		if(newName != this.filterValues.name || newPlatform != this.filterValues.platform || newGenre != this.filterValues.genre || newOrder != this.filterValues.order){
			this.filterValues.name = newName;
			this.filterValues.platform = newPlatform;
			this.filterValues.genre = newGenre;
			this.filterValues.order = newOrder;

			this.endOfList = 0;
			this.allLoaded = false;

			this.loadGames(true);
		}
	}

	listAtBottomListener(event){
		var element = event.target;
		if (element.scrollHeight - element.scrollTop === element.clientHeight){
			this.loadGames(false);
		}
	}

	loadGames(withFilter){
		if(!this.allLoaded){
			this.DALForReads.getFilteredGames(this.endOfList, this.toGet, this.filterValues.name, this.filterValues.platform, this.filterValues.genre, this.filterValues.order === "Ascending").subscribe(function(newGames){
				newGames = this.responseToModelService.convertGames(newGames);
				this.games = withFilter ? newGames : this.games.concat(newGames);

				this.endOfList = this.games.length;

				//Ha kevesebb jott, mint kertunk, akkor tobbet mar nem fogunk kerni
				if(newGames.length != this.toGet){
					this.allLoaded = true;
				}
			}.bind(this));
		}
	}
}
