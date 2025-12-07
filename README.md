# 1주차 — CPU와 스레드의 관계

- CS 개념: 프로세스, 스레드, 컨텍스트 스위칭
- Java 실험: Thread vs ExecutorService 동작 비교
- Spring 실무: @Async와 스레드풀 설정 (TaskExecutor)
- 
# 2주차 — 메모리 구조와 GC

- CS 개념: Stack / Heap / Garbage Collection

- Java 실험: 객체 생성 후 GC 로그 분석 (-XX:+PrintGCDetails)

- Spring 실무: Bean Scope과 객체 재사용 전략

# 3주차 — 네트워크와 HTTP

- CS 개념: TCP, HTTP 1.1 vs 2, Keep-Alive

- Java 실험: HttpURLConnection vs WebClient 요청 비교

- Spring 실무: 커넥션 풀 설정 (connectionTimeout, maxConnections)
  
# 4주차 — 데이터베이스와 트랜잭션

- CS 개념: 트랜잭션 ACID, Isolation Level, 락/데드락
  
- Java 실험: JDBC 트랜잭션 begin/commit/rollback

- Spring 실무: @Transactional 옵션, 트랜잭션 전파/격리 수준

# 5주차 — 캐시와 성능 최적화

- CS 개념: 캐시 구조(L1/L2/L3), locality, 캐시 히트

- Java 실험: ConcurrentHashMap 캐시 성능 비교

- Spring 실무: @Cacheable, Redis/Caffeine 캐시 적용

# 6주차 — 비동기 메시징과 이벤트

- CS 개념: 메시지큐 모델, Consistency, Consumer 패턴

- Java 실험: BlockingQueue Producer-Consumer

- Spring 실무: Spring Event, Kafka/RabbitMQ 기본 연동

# 7주차 — 보안과 인증/인가

- CS 개념: 암호화(AES/RSA), 해시, 인증 vs 인가

- Java 실험: JCA로 암호화, JWT 생성/검증

- Spring 실무: Spring Security, JWT 인증 흐름

# 8주차 — 모니터링과 운영

- CS 개념: Metrics/Logging/Tracing, 시스템 리소스 지표

- Java 실험: JMX, VisualVM으로 JVM 모니터링

- Spring 실무: Actuator, Micrometer, Prometheus/Grafana
