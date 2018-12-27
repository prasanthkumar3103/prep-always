FROM ubuntu  # to launch container which base image we use 

WORKDIR /opt/facebook  #setting working/build directory path

RUN apt-get-update && \  # it is core of docker command to define what to run
    apt-get-install -yq build-essential make git libsqlite3-dev sqlite3

COPY . /opt/facebooc #copying build context from current dir to /opt/facebook

RUN make all

EXPOSE 16000

CMD "bin/facebook"  # this is to telling container start command and be in it.


#steps to run 
#docker build -t ppk/facebooc:v1 .
#docker image ls
#docker image history ppk/facebooc:v1
#lunch container
#docker run -it -P ppk/facebooc:v1
#docker image push ppk/facebooc:v1
