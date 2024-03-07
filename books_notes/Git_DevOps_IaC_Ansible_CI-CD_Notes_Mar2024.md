====================================================================================================================
IaC, Ansible, DevOps, Jenkins etc.
====================================================================================================================
1. ChatGPT guidance:
	Prompt 1: I need to automate using IaC and script download, installation, and configuration of JDK, Doker, MicroK8s, apt install build-essential, and, apt install build-essential zlib1g-dev on Ubuntu linux. What is the best and user friendly opensource IaC tool I can use on Ubuntu for this? 
	Prompt 2: Now modify the above playbook to download JDK using wget https://download.oracle.com/graalvm/21/latest/graalvm-jdk-21_linux-x64_bin.tar.gz   and create directory /usr/lib/jvm/  and extract JDK archive to that location. After installation edit /etc/profile to append export statements for JAVA_HOME and GRAALVM_HOME pointing to jdk install location, and append JAVA_HOME/bin location to PATH variable.

	Response: you can leverage popular Infrastructure as Code (IaC) tools such as Ansible, Terraform, or Chef. Each of these tools has its strengths and can be used to achieve the desired automation tasks effectively.

However, considering the requirement for a user-friendly open-source IaC tool specifically on Ubuntu, Ansible stands out as a top choice due to its simplicity, agentless architecture, and broad community support. Ansible is well-suited for automating tasks on Ubuntu systems and offers easy-to-understand YAML-based playbooks for defining infrastructure configurations.

Here's how you can use Ansible to automate the installation and configuration of the mentioned software packages:

Install Ansible:
First, ensure that Ansible is installed on your Ubuntu system. 
You can install it via the package manager:

	sudo apt update
	sudo apt install ansible

Write Ansible Playbooks:
Here's a basic example playbook ('install_software.yaml'):


---

---

- name: Install JDK, Docker, MicroK8s, and dependencies
  hosts: localhost
  become: yes
  tasks:
    - name: Create directory for JDK installation
      file:
        path: /usr/lib/jvm
        state: directory

    - name: Download JDK tar.gz file
      get_url:
        url: https://download.oracle.com/graalvm/21/latest/graalvm-jdk-21_linux-x64_bin.tar.gz
        dest: /tmp/graalvm-jdk-21_linux-x64_bin.tar.gz

    - name: Extract JDK archive
      ansible.builtin.unarchive:
        src: /tmp/graalvm-jdk-21_linux-x64_bin.tar.gz
        dest: /usr/lib/jvm
        remote_src: yes

    - name: Append JAVA_HOME and GRAALVM_HOME to /etc/profile
      lineinfile:
        path: /etc/profile
        line: |
          export JAVA_HOME=/usr/lib/jvm/graalvm-jdk-21
          export GRAALVM_HOME=/usr/lib/jvm/graalvm-jdk-21

    - name: Append JDK bin directory to PATH in /etc/profile
      lineinfile:
        path: /etc/profile
        line: 'export PATH="$PATH:/usr/lib/jvm/graalvm-jdk-21/bin"'


    - name: Install Docker
      apt:
        name: docker.io
        state: present

    - name: Install MicroK8s
      apt:
        name: microk8s
        state: present

    - name: Install build-essential
      apt:
        name: build-essential
        state: present

    - name: Install zlib1g-dev
      apt:
        name: zlib1g-dev
        state: present

----

Run Ansible Playbooks: Execute the playbook on your Ubuntu system using the ansible-playbook command:


	ansible-playbook install_software.yaml



====================================================================================================================
Git Commands
====================================================================================================================
1. Git & GitHub configs: 
 Ref 1: https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup
 Ref 2: https://www.aicg.com/blog/how-to-show-or-change-your-git-username-or-email-address-from-terminal/

 The Github URL for this repository is: https://github.com/jeevkool/Quarkus_Vertx.git

 Add global git username and password for GitHub authentication, as follows at the Git command prompt:

		git config --global user.name "jeevkool"
		git config --global user.email jeevkool@hotmail.com
		git config --global user.password Key2free!

2. Verify by listing the Git configs:

		$ git config --list
		
3. Adding GitHub respository to Visual Studio Code:
On the VS Code Left side Ribbon, go to 'Source Control'. (Alternative: Ctrl+Shift+G G). THEN from the menu to the right of 'SOURCE CONTROL' click on '...' then pick 'Remote' > 'Add Remote' 
And then add the GitHub URL to the repository. Give it a name when it asks. Preferable GitHub <repository name>

4. To add files and push/upload changes to the GitHub respository ==> Either select a Commit & Push option from the Drop-Down of the Blue Button  "√ Commit" right below 'SOURCE CONTROL'.
Or first do a commit there, and then do the Push.

5. Developer 1/Machine 1 changes pushed to Git, AND Developer 2/Machine to PULL to their copy.
	First have Dev 1 do Commit & Push.
	Then have Dev 2 on machine 2 to do:
		$ git pull.
	If this shows merge conflicts with locally modified files, Dev 2 has to do a "commit" and "push" first before pulling.
	If Dev 2 or Machine 2 is a build machine (no changes made there) and still it shows conflict, then do a Hard reset to Discard local changes, by:

Ref: https://www.git-tower.com/learn/git/faq/git-force-pull
	
	First do: $ git reset --hard
	Then do: $ git pull

================================================================
Docker Commands:
================================================================
1: Install Docker on Ubuntu: Ref: https://www.simplilearn.com/tutorials/docker-tutorial/how-to-install-docker-on-ubuntu
IGNORE other steps/commands in the above article, like uninstall first, using snap etc. ONLY the below steps/commands are necessary:

	$ sudo apt-get update
	$ sudo apt install docker.io

Verify using:
	$ docker --version
	AND
	$ sudo docker run hello-world

	To display all the containers pulled, use the following command:
		$ sudo docker ps -a

2. IMPORTANT: To be able to Execute the Docker Command Without Sudo: 
	First, establish the Docker Group:

	$ sudo groupadd docker

Next, add the user in the Docker group:

	$ sudo usermod -aG docker $USER

3. START / STOP Docker daemon:
	Ref: https://stackoverflow.com/questions/42365336/how-to-stop-docker-under-linux

		$ sudo systemctl stop docker
		$ sudo systemctl start docker

	If there is no systemctl, or started Docker by 'service' THEN:
		$ sudo service docker start
		$ sudo service docker stop
	================================

4. Remove stopped Docker containers:
	Ref: https://middleware.io/blog/docker-cleanup/

	To remove stopped containers:
		$ docker container rm $(docker container ls -aq) 

	
====================================================================================================================
Rancher Kubernetes and K3S
====================================================================================================================
1. Rancher Desktop Kubernetes: Installation guide: 
https://docs.rancherdesktop.io/getting-started/installation/#linux
