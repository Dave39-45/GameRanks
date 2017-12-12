import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from "rxjs/Observable";

import {GameStruct} from "../models/serverStructs/element/GameStruct";
import {ReviewUser} from "../models/entity/ReviewUser";
import {Review} from "../models/entity/Review";
import {MainPageStruct} from "../models/serverStructs/page/MainPageStruct";
import {PublisherStruct} from "../models/serverStructs/element/PublisherStruct";
import {DeveloperStruct} from "../models/serverStructs/element/DeveloperStruct";
import {PublisherPageStruct} from "../models/serverStructs/page/PublisherPageStruct";
import {UserPageStruct} from "../models/serverStructs/page/UserPageStruct";
import {User} from "../models/entity/User";
import {UserPageReviewStruct} from "../models/entity/UserPageReviewStruct";

@Injectable()
export class DALForReadsService {
	serverUrl: string = "/api/";//"http://localhost:8080/api/";

  	constructor(private http: HttpClient) { }

	getHomePageData(): Observable<MainPageStruct>{
		return this.http.get<MainPageStruct>(this.serverUrl);
	}




	getGamesPageData(): Observable<GameStruct[]>{
		return this.http.get<GameStruct[]>(this.serverUrl + "game");
	}

	getFilteredGames(start: number, amount: number, name: string, platform: string, genre: string, ascending: boolean): Observable<GameStruct[]>{
		return this.http.get<GameStruct[]>(this.serverUrl + "game/list?start=" + start + "&amount=" + amount
			+ (name !== "" ? ("&name=" + name) : "")
			+ (platform !== "All" ? ("&platform=" + platform) : "")
			+ (genre !== "All" ? ("&genre=" + genre) : "")
			+ ("&ascending=" + ascending)
		);
	}

	getGamePageData(id: string): Observable<GameStruct>{
		return this.http.get<GameStruct>(this.serverUrl + "game/" + id);
	}




	getPublishersPageData(): Observable<PublisherStruct[]>{
		return this.http.get<PublisherStruct[]>(this.serverUrl + "publisher");
	}

	getFilteredPublishers(start: number, amount: number, name: string, ascending: boolean): Observable<PublisherStruct[]>{
		return this.http.get<PublisherStruct[]>(this.serverUrl + "publisher/list?start=" + start + "&amount=" + amount
			+ (name !== "" ? ("&name=" + name) : "")
			+ ("&ascending=" + ascending)
		);
	}

	getPublisherPageData(id: string): Observable<PublisherPageStruct>{
		return this.http.get<PublisherPageStruct>(this.serverUrl + "publisher/" + id);
	}

	getFilteredGamesForPublisher(publisherId: string, start: number, amount: number, name: string, platform: string, genre: string, ascending: boolean): Observable<GameStruct[]>{
		return this.http.get<GameStruct[]>(this.serverUrl + "publisher/" + publisherId + "/list?start=" + start + "&amount=" + amount
			+ (name !== "" ? ("&name=" + name) : "")
			+ (platform !== "All" ? ("&platform=" + platform) : "")
			+ (genre !== "All" ? ("&genre=" + genre) : "")
			+ ("&ascending=" + ascending)
		);
	}




	getDevelopersPageData(): Observable<DeveloperStruct[]>{
		return this.http.get<DeveloperStruct[]>(this.serverUrl + "developer");
	}

	getFilteredDevelopers(start: number, amount: number, name: string, ascending: boolean): Observable<DeveloperStruct[]>{
		return this.http.get<DeveloperStruct[]>(this.serverUrl + "developer/list?start=" + start + "&amount=" + amount
			+ (name !== "" ? ("&name=" + name) : "")
			+ ("&ascending=" + ascending)
		);
	}

	getDeveloperPageData(id: string): Observable<DeveloperStruct>{
		return this.http.get<DeveloperStruct>(this.serverUrl + "developer/" + id);
	}

	getFilteredGamesForDeveloper(developerId: string, start: number, amount: number, name: string, platform: string, genre: string, ascending: boolean): Observable<GameStruct[]>{
		return this.http.get<GameStruct[]>(this.serverUrl + "developer/" + developerId + "/list?start=" + start + "&amount=" + amount
			+ (name !== "" ? ("&name=" + name) : "")
			+ (platform !== "All" ? ("&platform=" + platform) : "")
			+ (genre !== "All" ? ("&genre=" + genre) : "")
			+ ("&ascending=" + ascending)
		);
	}




	getUserPageData(): Promise<UserPageStruct>{
		return this.http.get<UserPageStruct>(this.serverUrl + "user").toPromise();
	}
}


/*
new Publisher(3, 0, 'CD Projekt', 'cdp.jpg')

new Developer(3, 0, 'CD Projekt RED', 'cdpr.png')

new Game(3, 0, 'The Witcher 3: Wild Hunt', 3, 3, parsedatetime('18-05-2015', 'dd-MM-yyyy'), 'PC#PS4#XBOXONE', 'ACTION#ADVENTURE#RPG', 'tw3whW.jpg', 'tw3whL.jpg', 'tw3whC.jpg', 'tw3whI.png', '292030')

new Review(14,0,new ReviewUser("Tester", "https://cdn1.iconfinder.com/data/icons/ninja-things-1/1772/ninja-simple-512.png"),10,'Eleifend egestas.#Sed pharetra, felis eget varius ultrices.#Mauris ipsum porta elit.','Aliquam fringilla cursus purus.','PC','Nunc mauris elit, dictum eu, eleifend nec, malesuada ut, sem. Nulla interdum. Curabitur dictum. Phasellus in felis. Nulla tempor augue ac ipsum. Phasellus vitae mauris sit amet lorem semper auctor. Mauris vel turpis. Aliquam adipiscing lobortis risus. In mi pede, nonummy ut, molestie in, tempus eu, ligula. Aenean euismod mauris eu elit. Nulla facilisi. Sed neque. Sed eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus ornare. Fusce mollis. Duis sit amet diam eu dolor egestas rhoncus. Proin nisl sem, consequat nec, mollis vitae, posuere at, velit. Cras lorem lorem, luctus ut.','19-08-2015')

*/
