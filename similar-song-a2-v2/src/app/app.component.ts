import {Component, OnInit} from "@angular/core";
import {LoadingService} from "./service/loading.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private requesting: boolean = false;

  constructor(private loadingService: LoadingService) {

  }

  ngOnInit(): void {
    this.loadingService.requestEmiter.subscribe((req: boolean) => this.requesting = req);
  }
}
