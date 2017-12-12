import { Injectable } from '@angular/core';

import {MainPageStruct} from "./models/serverStructs/page/MainPageStruct";
import {Publisher} from "./models/entity/Publisher";
import {Developer} from "./models/entity/Developer";

@Injectable()
export class ResponseToModelService {
	constructor() {}

	convertGames(gameStructs){
		for(var struct of gameStructs){
			struct.game.genre = struct.game["genre"].split("#");
			struct.game.platform = struct.game["platform"].split("#");

			if(struct.steamData != null){
				struct.steamData = (JSON.parse(struct.steamData))[struct.game.steamId].data;
				struct.steamData["about_the_game"] = struct.steamData["about_the_game"].replace(/<[^<>]*>/g, " ");
			}
		}

		return gameStructs;
	}
	
	convertReview(review){
		review.pros = review["pros"].split("#");
		review.cons = review["cons"].split("#");
		
		return review;
	}

	convertReviews(reviews){
		for(var struct of reviews){
			struct.pros = struct["pros"].split("#");
			struct.cons = struct["cons"].split("#");
		}

		return reviews;
	}

	convertPublishers(publishers){
		for(var struct of publishers){
			struct.publisher = new Publisher(struct.publisher.id, struct.publisher.version, struct.publisher.name, struct.publisher.logo);
		}

		return publishers;
	}

	convertDevelopers(developers){
		for(var struct of developers){
			struct.developer = new Developer(struct.developer.id, struct.developer.version, struct.developer.name, struct.developer.logo);
		}

		return developers;
	}

	convertMainPageStruct(mainPageStruct): MainPageStruct{
		mainPageStruct.topThreeGames = this.convertGames(mainPageStruct.topThreeGames);
		mainPageStruct.topThreePublishers = this.convertPublishers(mainPageStruct.topThreePublishers);

		for(var struct of mainPageStruct.topThreeDevelopers){
			struct.developer = new Developer(struct.developer.id, struct.developer.version, struct.developer.name, struct.developer.logo);
		}

		return mainPageStruct;
	}

	convertGamePageData(gamePageStruct){
		gamePageStruct.game.genre = gamePageStruct.game["genre"].split("#");
		gamePageStruct.game.platform = gamePageStruct.game["platform"].split("#");

		if(gamePageStruct.steamData != null){
			gamePageStruct.steamData = (JSON.parse(gamePageStruct.steamData))[gamePageStruct.game.steamId].data;
			gamePageStruct.steamData["about_the_game"] = gamePageStruct.steamData["about_the_game"].replace(/<[^<>]*>/g, " ");
		}

		gamePageStruct.reviews = this.convertReviews(gamePageStruct.reviews);

		return gamePageStruct;
	}

	convertPublisherPageData(publisherPageStruct){
		publisherPageStruct.publisherStruct.publisher = new Publisher(publisherPageStruct.publisherStruct.publisher.id, publisherPageStruct.publisherStruct.publisher.version,
																	  publisherPageStruct.publisherStruct.publisher.name, publisherPageStruct.publisherStruct.publisher.logo);
		publisherPageStruct.publisherStruct.games = this.convertGames(publisherPageStruct.publisherStruct.games);

		return publisherPageStruct;
	}

	convertDeveloperPageData(developerPageStruct){
		developerPageStruct.developerStruct.developer = new Developer(developerPageStruct.developerStruct.developer.id, developerPageStruct.developerStruct.developer.version,
																	  developerPageStruct.developerStruct.developer.name, developerPageStruct.developerStruct.developer.logo);
		developerPageStruct.developerStruct.games = this.convertGames(developerPageStruct.developerStruct.games);

		return developerPageStruct;
	}

	convertUserPageData(userPageStruct){
		for(var struct of userPageStruct.userPageReviewStructs){
			struct.review.pros = struct.review["pros"].split("#");
			struct.review.cons = struct.review["cons"].split("#");
		}

		return userPageStruct;
	}
}
