import {Injectable, EventEmitter} from "@angular/core";
import {Http, Response} from "@angular/http";
import "rxjs/Rx";
import {Song} from "../model/song";
import {Observable} from "rxjs";

@Injectable()
export class SongService {

  private baseUrl: string = "http://localhost:5454/song";

  public songPublisher: EventEmitter<Song[]> = new EventEmitter<Song[]>();

  constructor(private http: Http) {
  }

  getSongs(artist: string, song: string, limit: number) {
    let url: string = this.baseUrl + "?song=" + song + "&artist=" + artist + "&limit=" + limit;

    return this.http.get(url)
      .map((response: Response) => (<Song[]>response.json()))
      .catch(error => {
        return Observable.throw(error.json());
      });
  }

  actualizeSongs(songs: Song[]) {
    this.songPublisher.emit(songs);
  }
}
