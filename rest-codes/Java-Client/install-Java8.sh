apt install openjdk-8-jdk &&	
echo 'export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64' >> $HOME/.bashrc &&
echo 'export PATH=$PATH:$JAVA_HOME/bin' >> $HOME/.bashrc &&
source ~/.bashrc
