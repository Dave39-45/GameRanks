import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { RoutingModule } from './routing/routing.module';

import { DisplayMessageService } from './display-message.service';
import { DALForReadsService } from './DAL/DALForReads.service';
import { DALForWritesService } from './DAL/DALForWrites.service';
import { ResponseToModelService } from "./ResponseToModel.service";
import { IsLoggedInService } from "./is-logged-in.service";

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { DeveloperComponent } from './developer/developer.component';
import { DevelopersComponent } from './developers/developers.component';
import { GameComponent } from './game/game.component';
import { GamesComponent } from './games/games.component';
import { PublisherComponent } from './publisher/publisher.component';
import { PublishersComponent } from './publishers/publishers.component';
import { RegisterComponent } from './register/register.component';
import { UserComponent } from './user/user.component';
import { MenuComponent } from './menu/menu.component';

@NgModule({
	declarations: [
		AppComponent,
		HomeComponent,
		DeveloperComponent,
		DevelopersComponent,
		GameComponent,
		GamesComponent,
		PublisherComponent,
		PublishersComponent,
		RegisterComponent,
		UserComponent,
		MenuComponent
	],
	imports: [
		BrowserModule,
		RoutingModule,
		HttpClientModule
	],
	providers: [DALForReadsService, DALForWritesService, DisplayMessageService, ResponseToModelService, IsLoggedInService],
	bootstrap: [AppComponent]
})
export class AppModule { }
