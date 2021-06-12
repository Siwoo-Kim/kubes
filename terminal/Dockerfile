FROM ubuntu:latest
RUN apt-get update; apt-get -y install curl;
ADD entry.sh /bin/entry.sh
ENTRYPOINT ["/bin/entry.sh"]