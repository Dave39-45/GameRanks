import {BaseModel} from "./BaseModel";

export class Publisher extends BaseModel{
	name: string;
	logo: string;
	listImage: string;
	bgColor: string;

	constructor(id: number, version: number, name: string, logo: string){
		super(id, version);
		this.name = name;
		this.logo = logo;
		this.listImage = logo.replace(".", "L.");

		//Felvesszuk, hogy milyen szinu a kep hattere, igy a wallpaper eseten olyan szinu lesz
		var canvas = document.createElement("canvas"),
			img = new Image();

		img.onload = function(){
			canvas.width = img.width;
			canvas.height = img.height;

			var context = canvas.getContext('2d');

			context.drawImage(img, 0, 0);
			var color = context.getImageData(0,0,1,1).data;
			this.bgColor = "rgb(" + color[0] + ", " + color[1] + ", " + color[2] + ")";
		}.bind(this);
		img.src = "../assets/img/publishers/" + this.listImage;
	}
}
