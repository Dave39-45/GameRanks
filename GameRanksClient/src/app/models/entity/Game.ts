import {BaseModel} from "./BaseModel";
import {Publisher} from "./Publisher";
import {Developer} from "./Developer";
//import {Timestamp} from "rxjs/Rx";

export class Game extends BaseModel{
	name: string = "";
	publisher: Publisher = null;
	developer: Developer = null;
	releaseDate: string = "";
	platform: string[] = [];
	genre: string[] = [];
	wallpaper: string;
	listImage: string = "";
	coverArt: string = "";
	smallImage: string = "";
	steamId: string = "";

	constructor(id: number, version: number, name: string, publisher: Publisher, developer: Developer, releaseDate: string,
				platform: string, genre: string, wallpaper: string, listImage: string, coverArt: string, smallImage: string, steamId: string){
		super(id, version);
		this.name = name;
		this.publisher = publisher;
		this.developer = developer;
		this.releaseDate = releaseDate;
		this.platform = platform.split("#");
		this.genre = genre.split("#");
		this.wallpaper = wallpaper;
		this.listImage = listImage;
		this.coverArt = coverArt;
		this.smallImage = smallImage;
		this.steamId = steamId;
	}
}
