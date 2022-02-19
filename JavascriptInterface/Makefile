id := B2D120D19F0021
pkg := com.segway.robotic.app
pkgs := com.segway.robotic.launcher

.PHONY: build clean log mock

all: build mock
	echo build successful

mock:
	@npm start --prefix ./mock

build:
	./gradlew :app:assembleAlpha
	adb -s $(id) install -r -t -d app/build/outputs/apk/alpha/*.apk
	adb -s $(id) shell am start -n $(pkg)/.setting.MainActivity -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

builds:
	./gradlew :daemon:assembleAlpha
	adb -s $(id) install -r -t -d daemon/build/outputs/apk/alpha/*.apk
	adb -s $(id) shell am start -n $(pkgs).view.test.TestActivity -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

clean:
	./gradlew clean

desk:
	adb -s $(id) shell input keyevent 3

logcat:
	adb -s $(id) shell ps -ef | grep $(pkg) | awk '{print $$2}' | xargs -I P adb -s $(id)  shell logcat --pid P

logcatc:
	adb -s $(id) shell ps -ef | grep $(pkg).clock | awk '{print $$2}' | xargs -I P adb shell logcat --pid P

stop: stopa stops

stopa:
	adb -s $(id) shell am force-stop $(pkg)

stops:
	adb -s $(id) shell am force-stop $(pkgs)

starta:
	adb -s $(id) shell am start -n $(pkg)/.container.MainActivity -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

startas:
	adb -s $(id) shell am start -n $(pkg)/.container.SettingActivity -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

starts:
	adb -s $(id) shell am start -n "$(pkgs)/$(pkgs).view.test.TestActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER

restarta: stopa starta

restarts: stops starts

removea:
	adb -s $(id) uninstall $(pkg)

removes:
	adb -s $(id) uninstall $(pkgs)

robot:
	 adb -s $(id) shell cat /data/system/users/0/settings_secure.xml| grep robot

config: msg
	adb -s $(id) push config.json /sdcard/GX/config.json
	adb -s $(id) shell settings put secure robot_id cube05
	adb -s $(id) shell settings put secure robot_key Ninebot123

catconfig:
	-adb -s $(id) shell cat /sdcard/GX/config.json
	-adb -s $(id) shell cat /sdcard/segway_app_package.json

msg:
	adb -s $(id) install -r messageservice-release-signed.apk

voldown:
	adb -s $(id) shell input keyevent 25

volup:
	adb -s $(id) shell input keyevent 24

rma:
	adb -s $(id) root
	adb -s $(id) remount
	adb -s $(id) shell "rm -rf oem/bundled_persist-app/segway-app-signed/"
	adb -s $(id) reboot

rmtrunk:
	adb -s $(id) root
	adb -s $(id) remount
	adb -s $(id) shell "rm -rf oem/bundled_persist-app/trunkservice-release-signed/"
	adb -s $(id) reboot

rms:
	adb -s $(id) root
	adb -s $(id) remount
	adb -s $(id) shell "rm -rf oem/bundled_persist-app/segway-launcher-signed/"
	adb -s $(id) reboot

rmapps:
	adb -s $(id) root
	adb -s $(id) remount
	adb -s $(id) shell "rm -rf oem/bundled_persist-app/GxAppService-release-signed/"
	adb -s $(id) reboot

root:
	adb -s $(id) root
	adb -s $(id) remount
	adb -s $(id) disable-verity
	adb -s $(id) reboot

pullc:
	adb -s $(id) pull sdcard/segway_app_package.json .

pushc:
	adb -s $(id) push segway_app_package.json sdcard

log:
	rm -rf logs
	adb -s $(id) pull /sdcard/ex/app/logs/ .

rmconfig:
	adb -s $(id) shell rm /sdcard/segway_app_package.json
	adb -s $(id) shell rm /sdcard/GX/config.json

ip:
	@./phone_ip.sh
	adb -d tcpip 5555
	adb -d connect $(shell make ip):5555


id:
	@adb devices | grep "device$$"|head -n 1 |awk '{print $$1}'

id2:
	@adb devices | grep "device$$"|head -2 | tail -1 |awk '{print $$1}'

PATTERN := \\w\\+$$
setid:
	sed -i "/^id :=/s/$(PATTERN)/$(shell make id)/g" Makefile

setid2:
	sed -i "/^id :=/s/$(PATTERN)/$(shell make id2)/g" Makefile

battery:
	adb -s $(id) shell "mkdir /sdcard/ex/battery/ && cd /sdcard/ex/battery && echo 99>value"

charge:
	adb -s $(id) shell "cd /sdcard/ex/battery/ && echo 1 > state"

chargeoff:
	adb -s $(id) shell "mkdir -p /sdcard/ex/battery"
	adb -s $(id) shell "cd /sdcard/ex/battery/ && echo 0 > state"

charge_state:
	@adb -s $(id) shell cat /sdcard/ex/battery/state

intcolor:
	#grep -oh -E "#[A-F0-9]{6,8}" -r . --include="*.xml"
	grep -o -E "#[A-F0-9]{6,8}" -r . --include="*.xml"

dumpfrag:
	adb -s $(id) shell dumpsys activity top | grep "Added Frag" -A 1

dumpact:
	adb -s $(id) shell dumpsys activity com.segway.robotic.app | grep "ACTIVITY " -A 5

wakealarm:
	adb -s $(id) root
	adb -s $(id) shell "echo $$(expr $$(date +%s) + 120) > /sys/class/rtc/rtc0/wakealarm"

catalarm:
	adb -s $(id) shell "cat /sys/class/rtc/rtc0/wakealarm"
	@date +%s

wifion:
	adb -s $(id) shell svc wifi enable

wifioff:
	adb -s $(id) shell svc wifi disable

disable:
	adb -s $(id) shell pm disable $(pkg)/.container.MainActivity

enable:
	adb -s $(id) shell pm enable $(pkg)/.container.MainActivity

disablec:
	adb -s $(id) shell pm disable shitao.li.controler/.MainActivity

enablec:
	adb -s $(id) shell pm enable shitao.li.controler/.MainActivity

sh:
	adb -s $(id) shell

sh2:
	adb -s $(shell make id2) shell

robot_key:
	@adb -s $(id) shell settings get secure robot_key

robot_id:
	@adb -s $(id) shell settings get secure robot_id

enablel:
	adb -s ${id} root
	adb -s ${id} shell pm enable com.android.launcher3/.Launcher

d: enablel desk

kill: 
	adb -s $(id) root && adb -s $(id) shell ps -ef | grep $(pkg) | awk '{print $$2}' | xargs -I P adb -s $(id) shell kill P

maven:
	sed -i "/repositories {/a \ \t\tmaven { url 'https://maven.aliyun.com/repository/google' }" build.gradle
	sed -i "/repositories {/a \ \t\tmaven { url 'https://maven.aliyun.com/repository/central' }" build.gradle

backnav:
	adb -s $(id) shell settings put global activity_mode 0

scrcpy:
	scrcpy -s $(id)
