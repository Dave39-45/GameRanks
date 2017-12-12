export class BaseModel {
	id: number = 0;
	version: number = 0;

	constructor(id?: number, version?: number){
		this.id = id;
		this.version = version;
	}
}
