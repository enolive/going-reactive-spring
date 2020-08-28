import {Component, NgZone, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

interface HugDocument {
  from: string;
  timeStamp: Date;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend';
  hugs: HugDocument[] = [];
  private MAX_ITEMS = 10;

  constructor(private zone: NgZone) {
  }

  private static transformToDocument(data: string): HugDocument {
    return JSON.parse(data);
  }

  ngOnInit(): void {
    this.observeHugsStream()
        .pipe(map(data => AppComponent.transformToDocument(data)))
        .subscribe(hug => {
          this.hugs = [hug, ...this.hugs.slice(0, this.MAX_ITEMS - 1)];
        });
  }

  private observeHugsStream(): Observable<string> {
    return new Observable(observer => {
      const eventSource = new EventSource('http://localhost:8081/hugs/stream');
      eventSource.onmessage = message => {
        this.zone.run(() => observer.next(message.data));
      };
    });
  }
}
