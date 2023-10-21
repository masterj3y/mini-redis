FROM ubuntu:22.04
COPY ./target/release/mini-redis app
EXPOSE 6379
ENTRYPOINT [ "./app" ]