FROM ubuntu:22.04
COPY ./target/release/mini-redis-server app
EXPOSE 6379
ENTRYPOINT [ "./app" ]