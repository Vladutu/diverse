import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {AppComponent} from "./app.component";
import {HeaderComponent} from "./header/header.component";
import {SearchComponent} from "./search/search.component";
import {SongService} from "./service/song.service";
import { SongsResultComponent } from './songs-result/songs-result.component';
import { SongItemComponent } from './song-item/song-item.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SearchComponent,
    SongsResultComponent,
    SongItemComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ReactiveFormsModule
  ],
  providers: [SongService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
