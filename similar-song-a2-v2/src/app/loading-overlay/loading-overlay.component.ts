import {Component, OnInit, Input} from "@angular/core";

@Component({
  selector: 'app-loading',
  templateUrl: './loading-overlay.component.html',
  styleUrls: ['./loading-overlay.component.css']
})
export class LoadingOverlayComponent implements OnInit {


  @Input()
  private show: boolean;

  constructor() {
  }

  ngOnInit() {
  }

}
