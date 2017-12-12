import {Developer} from "../../entity/Developer";
import {GameStruct} from "./GameStruct";

export class DeveloperStruct{
	developer: Developer;
	avgScore: number;
	rankTitle: string;
	games: GameStruct[];

	constructor(developer: Developer, avgScore: number, rankTitle: string, games: GameStruct[]){
		this.developer = developer;
		this.avgScore = avgScore;
		this.rankTitle = rankTitle;
		this.games = games;
	}
}
