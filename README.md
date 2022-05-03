# 💚👶wooaham_server👶💚
```
우리아이함께(우아함) : 자녀의 스케줄을 하나의 앱으로 관리할 수 있으면 좋겠다는 생각으로 시작하였으며 해당 어플 하나로 시간 알림, 자녀 위치, 학교의 공지사항 등의 정보를 한 번에 손쉽게 받아볼 수 있도록 하여 바쁜 학부모들에게 편의를 제공한다.
주변 편의시설 및 아동지킴이 집 위치를 제공함으로써 학생들에게 편의를 제공하고, 공지 및 알림 사항 읽은 사람 목록을 표시함으로써 교사들에게도 편의를 제공한다.
```

## git 사용 규칙
master 에서 직접 작업하지 않는다 (master로 바로 push 하지 않는다)
branchname : 본인 이니셜/작업일자

<br/>


## 서버 배포(AWS EC2내에서 실행)
**1. AWS 접속 -- ssh 인증으로 콘솔에 접속**

**2. /home/ubuntu/app/wooaham_server/로 이동**

```
cd app/wooaham_server/
```
**3. git 에 있는 최신 master 브랜치 반영**

```
git pull origin master
```
**4. 배포할 버전 빌드 - 아직 이름 붙이는 건 X**

```
./gradlew build
```
**권한 관련 문제 발생시**
```
sudo chmod 777 ./gradlew && ./gradlew build
```
**5. 빌드 성공 후**

```
cd build/libs && ls
```
보이는 파일중
wooaham_server-0.0.1-SNAPSHOT-plain.jar  wooaham_server-0.0.1-SNAPSHOT.jar 
둘중 뒤에 plain 없는 .jar 실행


# RUN SERVER
**1. 포그라운드 (터미널 닫으면 서버 닫힘)**
```
java -jar wooaham_server-0.0.1-SNAPSHOT.jar
```
**2. 백그라운드 (터미널 닫아도 서버 유지)**
**- 실행**
```
nohup java -jar build/libs/wooaham_server-0.0.1-SNAPSHOT.jar &
```
- nohup은 터미널을 꺼도 애플리케이션이 꺼지지 않도록 하는 명령어
- &는 애플리케이션이 백그라운드에서 돌아갈 수 있도록 하는 명령어

 
**백그라운드 로그 확인**
```
tail -f nohup.out
```
### 서버 배포 전에 꼭 이전 프로세스 종료시켜야 한다.
**종료시키기 위한 프로세스 아이디 확인**
```
sudo netstat -ntlp | grep :9000
```
**백그라운드 실행 종료**
```
kill -9 [프로세스 아이디]
```
