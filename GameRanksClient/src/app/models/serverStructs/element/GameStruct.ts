import {Game} from "../../entity/Game";
import {Review} from "../../entity/Review";

export class GameStruct{
	game: Game;
	avgScore: number;
	rankTitle: string;
	steamData: Object = null;
	reviews: Review[];

	constructor(game: Game, avgScore: number, rankTitle: string, steamData: Object/*string*/, reviews: Review[]){
		this.game = game;
		this.avgScore = avgScore;
		this.rankTitle = rankTitle;
		this.reviews = reviews;

		if(steamData != null){
			this.steamData = steamData["" + game.steamId]["data"];//JSON.parse(steamData);
			this.steamData["about_the_game"] = this.steamData["about_the_game"].replace(/<[^<>]*>/g, "");
		}
	}
}
