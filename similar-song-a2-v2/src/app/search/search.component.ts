import {Component, OnInit} from "@angular/core";
import {Song} from "../model/song";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SongService} from "../service/song.service";
import {LoadingService} from "../service/loading.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  private song: Song = null;
  private myFrom: FormGroup;
  private error: boolean = false;

  constructor(private songService: SongService, private loadingService: LoadingService) {
  }

  ngOnInit() {
    this.myFrom = new FormGroup({
      'artist': new FormControl('', [Validators.required]),
      'song': new FormControl('', [Validators.required]),
      'limit': new FormControl('', [Validators.required, Validators.pattern("^[1-9][0-9]?$|^100$")])
    });
  }

  onSubmit() {
    let val = this.myFrom.value;
    this.loadingService.actualize(true);
    this.songService
      .getSongs(val.artist, val.song, +val.limit)
      .subscribe(
        (data: Song[]) => {
          this.song = data[0];
          let similarSongs: Song[] = data.slice(1, data.length + 1);
          this.error = false;
          this.songService.actualizeSongs(similarSongs);
          this.loadingService.actualize(false);
        },
        error => {
          this.error = true;
          this.song = null;
          this.songService.actualizeSongs([]);
          this.loadingService.actualize(false);
        }
      );
  }

}
