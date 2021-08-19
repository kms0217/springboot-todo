## 요구사항
  - TODO 전체 조회 --- 1   
  - 특정 TODO 조회 --- 2
  - TODO 추가 --- 3
  - 특정 TODO 수정 --- 4
  - 전체 TODO 삭제 --- 5
  - 특정 TODO 삭제 --- 6
  
## API

|Method|Endpoint|request|response|기능|
|---|---|---|---|---|
|GET|/|.|<pre>[<br> {<br>  "id" : 1, <br>  "title" : "아침먹기", <br>  "order" : 0, <br>  "completed" : false<br>  "url" : "http://localhost:9091/1"<br>},<br>{<br>  "id" : 2,<br>  "title" : "저녁먹기", <br>  "order" : 0, <br>  "completed" : false<br>  "url" : "http://localhost:9091/2"<br> },<br> ...<br>]</pre>|1|
|GET|/{:id}|.|<pre>{<br>  "id" : 1, <br>  "title" : "아침먹기", <br>  "order" : 0, <br>  "completed" : false<br>  "url" : "http://localhost:9091/1" <br> }</pre>|2|
|POST|/|<pre>{<br>  "title" : "새로생성"<br>} </pre>|<pre>{<br>  "id" : 3, <br>  "title" : "새로생성", <br>  "order" : 0, <br>  "completed" : false <br>  "url" : "http://localhost:9091/3" <br>}</pre>|3|
|PATCH|/{:id}|<pre>{<br>  "title" : "수정~"<br>} </pre> <pre>{<br>  "completed" : true<br>}</pre>|<pre>{<br>  "id" : 3, <br>  "title" : "수정~", <br>  "order" : 0, <br>  "completed" : false <br>  "url" : "http://localhost:9091/3"  <br>}</pre><pre>{<br>  "id" : 3,<br>  "title" : "수정~",<br>  "completed" : true <br>  "url" : "http://localhost:9091/3" <br>}</pre>|4|
|DELETE|/|.|200 Status Code|5|
|DELETE|/{:id}|.|200 Status Code|6|
