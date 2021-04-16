## Run cypress in interactive mode 
1. get the IP address of the host machine and allow X11 to accept incoming connections from that IP address
    - IP=$(ipconfig getifaddr en0)
    - /usr/X11/bin/xhost + $IP
2. then pass the environment variable DISPLAY to show Cypress GUI on the host system
    - DISPLAY=$IP:0

3. Run in the root project directory: `docker-compose -f docker-compose.yml -f e2e/cy-open.yml up --exit-code-from cypress`