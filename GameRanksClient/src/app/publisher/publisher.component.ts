import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import {PublisherPageStruct} from "../models/serverStructs/page/PublisherPageStruct";
import {DALForReadsService} from "../DAL/DALForReads.service";
import {ResponseToModelService} from "../ResponseToModel.service";

declare var google:any;

@Component({
  selector: 'app-publisher',
  templateUrl: './publisher.component.html',
  styleUrls: ['./publisher.component.css']
})
export class PublisherComponent implements OnInit {
	publisherPageStruct: PublisherPageStruct;

	constructor(private DALForReads: DALForReadsService, private activatedRoute: ActivatedRoute, private responseToModelService: ResponseToModelService) {

	}

	async ngOnInit() {
	  	window.scrollTo(0, 0);

		await this.DALForReads.getPublisherPageData(this.activatedRoute.snapshot.paramMap.get("id")).subscribe(function(result){
			this.publisherPageStruct = this.responseToModelService.convertPublisherPageData(result);

			this.GamesHandler.init(this);
			this.ChartsHandler.init(this);
		}.bind(this));
	}




	//https://developers.google.com/chart/interactive/docs/gallery/piechart
	ChartsHandler = {
		parentThis: null,

		ChartData: {
			genreChartColors: ["rgb(200,200,200)", "rgb(180,180,180)", "rgb(160,160,160)", "rgb(140,140,140)", "rgb(120,120,120)", "rgb(100,100,100)", "rgb(80,80,80)", "rgb(60,60,60)", "rgb(40,40,40)", "rgb(20,20,20)"],
			PC: "black",
			PS4: "rgb(4,105,177)",
			XBOXONE: "rgb(18,126,17)",
			platformChartColors: ["black", "rgb(4,105,177)", "rgb(18,126,17)"],

			optionsForPieChart: {
				legend: {
					position: "none"
				},

				colors: null,	//Egyeduli kulonbseg a diagrammok kozott
				backgroundColor: "none"
			}
		},

		init(parentThis){
			this.parentThis = parentThis;

			google.charts.load('current', {packages: ['corechart']});

			//Csak azert igy, hogy a drawChart() -bol is hivatkozhassunk az objektumra (mashogy nem lehetne)
			var that = this;
			google.charts.setOnLoadCallback(function(){that.displayStatistics(that)});

		},

		displayStatistics(){
			this.displayNumberOfGamesPerGenre();
			this.displayNumberOfGamesPerPlatform();
			this.displayNumberOfReviewsPerPlatform();
			this.displayAvgScorePerPlatform();
		},

		displayNumberOfGamesPerGenre(){
			var categories = this.getCategoryArray(this.parentThis.publisherPageStruct.statisticStruct.numberOfGamesPerGenre);

			//Az elso harom diagram eseten nincsenek feliratok, igy ures szovegu tomb parost rakunk belejuk
			this.parentThis.publisherPageStruct.statisticStruct.numberOfGamesPerGenre.unshift(["", ""]);

			//Mindig aktualisan a sajat szint vesszuk fel
			var options = {};
			Object.assign(options, this.ChartData.optionsForPieChart);
			options["colors"] = this.ChartData.genreChartColors;

			this.createPieChart(document.getElementById("pubDevChartGenre"), this.parentThis.publisherPageStruct.statisticStruct.numberOfGamesPerGenre, options);
			this.addLegend(document.getElementById("pubDevLegendGenre"), this.ChartData.genreChartColors, categories);
		},

		displayNumberOfGamesPerPlatform(){
			var categories = this.getCategoryArray(this.parentThis.publisherPageStruct.statisticStruct.numberOfGamesPerPlatform);
			this.parentThis.publisherPageStruct.statisticStruct.numberOfGamesPerPlatform.unshift(["", ""]);

			var options = {};
			Object.assign(options, this.ChartData.optionsForPieChart);
			options["colors"] = this.getColorArrayForPlatform(this.parentThis.publisherPageStruct.statisticStruct.numberOfGamesPerPlatform);

			this.createPieChart(document.getElementById("pubDevChartGamesPerPlatform"), this.parentThis.publisherPageStruct.statisticStruct.numberOfGamesPerPlatform, options);
			this.addLegend(document.getElementById("pubDevLegendGamesPerPlatform"), options["colors"], categories);
		},

		displayNumberOfReviewsPerPlatform(){
			var categories = this.getCategoryArray(this.parentThis.publisherPageStruct.statisticStruct.numberOfReviewsPerPlatform);
			this.parentThis.publisherPageStruct.statisticStruct.numberOfReviewsPerPlatform.unshift(["", ""]);

			var options = {};
			Object.assign(options, this.ChartData.optionsForPieChart);
			options["colors"] = this.getColorArrayForPlatform(this.parentThis.publisherPageStruct.statisticStruct.numberOfReviewsPerPlatform);

			this.createPieChart(document.getElementById("pubDevChartReviewsPerPlatform"), this.parentThis.publisherPageStruct.statisticStruct.numberOfReviewsPerPlatform, options);
			this.addLegend(document.getElementById("pubDevLegendReviewsPerPlatform"), options["colors"], categories);
		},

		displayAvgScorePerPlatform(){
			//Az utolsonal van felirat
			this.parentThis.publisherPageStruct.statisticStruct.avgScorePerPlatform.unshift(["Platform", "Score", { role: 'style' }]);
			var length = this.parentThis.publisherPageStruct.statisticStruct.avgScorePerPlatform.length;

			//Felvesszuk a szineket az oszlopokhoz
			for(var i=1; i<length; ++i){
				this.parentThis.publisherPageStruct.statisticStruct.avgScorePerPlatform[i].push(this.ChartData[this.parentThis.publisherPageStruct.statisticStruct.avgScorePerPlatform[i][0]]);
			}

			var options = {
				vAxis: {ticks: [1,2,3,4,5,6,7,8,9,10]},
				backgroundColor: "none",
				legend: {position: "none"}
			};

			this.createColumnChart(document.getElementById("pubDevChartScorePerPlatform"), this.parentThis.publisherPageStruct.statisticStruct.avgScorePerPlatform, options);
		},

		getColorArrayForPlatform(data){
			var colors = [];

			for(var subArr of data){
				if(this.ChartData.hasOwnProperty(subArr[0])){
					colors.push(this.ChartData[subArr[0]]);
				}
			}

			return colors;
		},

		getCategoryArray(data){
			var categories = [];

			for(var subArr of data){
				categories.push(subArr[0]);
			}

			return categories;
		},

		createPieChart(container, data, options){
			var chart = new google.visualization.PieChart(container);
			chart.draw(google.visualization.arrayToDataTable(data), options);
		},

		createColumnChart(container, data, options){
			var chart = new google.visualization.ColumnChart(container);
			chart.draw(google.visualization.arrayToDataTable(data), options);
		},

		addLegend: function(parent, colors, texts){
			var length = texts.length;

			for(var i=0; i<length; ++i){
				var container = document.createElement("div"),
					colorElement = document.createElement("div"),
					textElement = document.createElement("span");

				colorElement.style.backgroundColor = colors[i];
				textElement.innerHTML = texts[i];

				container.appendChild(colorElement);
				container.appendChild(textElement);
				parent.appendChild(container);
			}
		}
	};




	GamesHandler = {
		parentThis: null,
		endOfList: 0,
		toGet: 10,
		allLoaded: false,

		filterOpen: false,
		filterValues: {
			name: "",
			platform: "All",
			genre: "All",
			order: "Decreasing"
		},

		elements: {
			filterOpener: null,
			filter: null,
			filterName: null,
			filterPlatform: null,
			filterGenre: null,
			filterOrder: null
		},

		init(parentThis){
			this.parentThis = parentThis;
			this.endOfList = parentThis.publisherPageStruct.publisherStruct.games.length;

			this.elements.filterOpener = document.getElementById("listFilterOpener");
			this.elements.filter = document.getElementById("listFilter");
			this.elements.filterName = document.getElementById("filterName");
			this.elements.filterPlatform = document.getElementById("filterPlatform");
			this.elements.filterGenre = document.getElementById("filterGenre");
			this.elements.filterOrder = document.getElementById("filterOrder");

			document.getElementById("list").addEventListener("scroll", this.listAtBottomListener.bind(this));
		},

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
		},

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
		},

		listAtBottomListener(event){
			var element = event.target;
			if (element.scrollHeight - element.scrollTop === element.clientHeight){
				this.loadGames(false);
			}
		},

		loadGames(withFilter){
			if(!this.allLoaded){
				this.parentThis.DALForReads.getFilteredGamesForPublisher(this.parentThis.activatedRoute.snapshot.paramMap.get("id"), this.endOfList, this.toGet, this.filterValues.name,
																			 this.filterValues.platform, this.filterValues.genre, this.filterValues.order === "Ascending").subscribe(function(newGames){
					newGames = this.parentThis.responseToModelService.convertGames(newGames);
					this.parentThis.publisherPageStruct.publisherStruct.games = withFilter ? newGames : this.parentThis.publisherPageStruct.publisherStruct.games.concat(newGames);

					console.log(newGames);

					this.endOfList = this.parentThis.publisherPageStruct.publisherStruct.games.length;

					//Ha kevesebb jott, mint kertunk, akkor tobbet mar nem fogunk kerni
					if(newGames.length != this.toGet){
						this.allLoaded = true;
					}
				}.bind(this));
			}
		}
	};
}
