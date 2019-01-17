#!/usr/bin/env bash
set -euo pipefail

ppcenterId=${appcenter_pager_id}
appcenterToken=${appcenter_token}
appcenterAppOwner=guido.rubin-pager.com
appcenterAppName=PagerApp
path="app/build/outputs/apk/debug/app-debug.apk"
appcenterUrl="https://api.appcenter.ms/v0.1/apps/${appcenterAppOwner}/${appcenterAppName}/release_uploads"
curl -v -F "status=2" -F "notifytatus=2" -F "notify=1" -F "ipa=@${path}" -H "X-API-Token: ${appcenterToken}" "${appcenterUrl}"
