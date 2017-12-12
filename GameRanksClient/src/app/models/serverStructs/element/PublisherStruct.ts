import {Publisher} from "../../entity/Publisher";
import {GameStruct} from "./GameStruct";

export class PublisherStruct{
	publisher: Publisher;
	avgScore: number;
	rankTitle: string;
	games: GameStruct[];

	constructor(publisher: Publisher, avgScore: number, rankTitle: string, games: GameStruct[]){
		this.publisher = publisher;
		this.avgScore = avgScore;
		this.rankTitle = rankTitle;
		this.games = games;
	}
}
