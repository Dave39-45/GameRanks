import {GameStruct} from "../element/GameStruct";
import {PublisherStruct} from "../element/PublisherStruct";
import {DeveloperStruct} from "../element/DeveloperStruct";

export class MainPageStruct{
	topThreeGames: GameStruct[];
	topThreePublishers: PublisherStruct[];
	topThreeDevelopers: DeveloperStruct[];

	constructor(topThreeGames: GameStruct[], topThreePublishers: PublisherStruct[], topThreeDevelopers: DeveloperStruct[]){
		this.topThreeGames = topThreeGames;
		this.topThreePublishers = topThreePublishers;
		this.topThreeDevelopers = topThreeDevelopers;
	}
}
