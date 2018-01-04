import {Component, OnInit} from "@angular/core";
import {Song} from "../model/song";
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {SongService} from "../service/song.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  private song: Song = null;
  private myFrom: FormGroup;
  private error: boolean = false;

  constructor(private songService: SongService) {
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

    this.songService
      .getSongs(val.artist, val.song, +val.limit)
      .subscribe(
        (data: Song[]) => {
          this.song = data[0];
          let similarSongs: Song[] = data.slice(1, data.length + 1);
          this.error = false;
          this.songService.actualizeSongs(similarSongs);
        },
        error => {
          this.error = true;
          this.song = null;
          this.songService.actualizeSongs([]);
        }
      );
  }

}
