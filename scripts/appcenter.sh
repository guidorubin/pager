#!/usr/bin/env bash
set -euo pipefail

ppcenterId=${appcenter_pager_id}
appcenterToken=${appcenter_token}
appcenterAppOwner=guido.rubin-pager.com
appcenterAppName=CircleApp
path="app/build/outputs/apk/debug/app-debug.apk"
appcenterUrl="https://api.appcenter.ms/v0.1/apps/${appcenterAppOwner}/${appcenterAppName}/release_uploads"
curl -d '{"release_id": 0}' -v -F "status=2" -F "notifytatus=2" -F "notify=1" -F "data=@${path}" -H 'Content-Type: application/json' "X-API-Token: ${appcenterToken}" "${appcenterUrl}"
