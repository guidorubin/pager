#!/usr/bin/env bash
set -euo pipefail

ppcenterId=${appcenter_pager_id}
appcenterToken=${appcenter_token}
appcenterAppOwner=guido.rubin-pager.com
appcenterAppName=CircleApp
path="app/build/outputs/apk/debug/app-debug.apk"
appcenterUrl="https://api.appcenter.ms/v0.1/apps/${appcenterAppOwner}/${appcenterAppName}/release_uploads"
#curl -d '{"release_id": 0}' -v "data=@${path}" -H 'Content-Type: application/json' "X-API-Token: ${appcenterToken}" "${appcenterUrl}"


#response=$(curl -X POST "${appcenterUrl}" -H "accept: application/json" -H "X-API-Token: ${appcenterToken}" -H "Content-Type:application/json" -d "{ \"release_id\": 0}" | jq -r '.upload_url')
#uploadUrl=$(jq -r '.upload_url' ${response})



curl -X POST "${appcenterUrl}" -H "accept: application/json" -H "X-API-Token: ${appcenterToken}" -H "Content-Type:application/json" -d "{ \"release_id\": 0}" | jq -r '.upload_id'


curl -F "ipa=@${path}" $(curl -X POST "${appcenterUrl}" -H "accept: application/json" -H "X-API-Token: ${appcenterToken}" -H "Content-Type:application/json" -d "{ \"release_id\": 0}" | jq -r '.upload_url')


curl -X PATCH --header 'Content-Type: application/json' --header 'Accept: application/json' --header 'X-API-Token: ${appcenterToken}' -d '{ "status": "committed"  }' "${appcenterUrl}/$(curl -X POST "${appcenterUrl}" -H "accept: application/json" -H "X-API-Token: ${appcenterToken}" -H "Content-Type:application/json" -d "{ \"release_id\": 0}" | jq -r '.upload_id')"