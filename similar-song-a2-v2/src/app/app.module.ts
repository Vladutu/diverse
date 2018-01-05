import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {HeaderComponent} from "./header/header.component";
import {SearchComponent} from "./search/search.component";
import {SongsResultComponent} from "./songs-result/songs-result.component";
import {SongItemComponent} from "./song-item/song-item.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {SongService} from "./service/song.service";
import {SafePipe} from "./pipe/SafePipe";
import {LoadingOverlayComponent} from "./loading-overlay/loading-overlay.component";
import {LoadingService} from "./service/loading.service";
import {BsModalModule} from 'ng2-bs3-modal';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SearchComponent,
    SongsResultComponent,
    SongItemComponent,
    SafePipe,
    LoadingOverlayComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule,
    BsModalModule
  ],
  providers: [SongService, LoadingService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
