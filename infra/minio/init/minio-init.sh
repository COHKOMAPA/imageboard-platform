#!/bin/sh

set -e

ALIAS="local"
POLICY_NAME="media-service-policy"
POLICY_FILE="/tmp/media-service-policy.json"

mc alias set \
  "$ALIAS" \
  http://minio:9000 \
  "$MINIO_ROOT_USER" \
  "$MINIO_ROOT_PASSWORD"

mc mb \
  --ignore-existing \
  "$ALIAS/$MINIO_BUCKET_NAME"

cat > "$POLICY_FILE" <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetBucketLocation",
        "s3:ListBucket",
        "s3:ListBucketMultipartUploads"
      ],
      "Resource": [
        "arn:aws:s3:::$MINIO_BUCKET_NAME"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:PutObject",
        "s3:DeleteObject",
        "s3:AbortMultipartUpload",
        "s3:ListMultipartUploadParts"
      ],
      "Resource": [
        "arn:aws:s3:::$MINIO_BUCKET_NAME/*"
      ]
    }
  ]
}
EOF

mc admin user add \
  "$ALIAS" \
  "$MINIO_MEDIA_ACCESS_KEY" \
  "$MINIO_MEDIA_SECRET_KEY"

mc admin policy create \
  "$ALIAS" \
  "$POLICY_NAME" \
  "$POLICY_FILE"

mc admin policy attach \
  "$ALIAS" \
  "$POLICY_NAME" \
  --user "$MINIO_MEDIA_ACCESS_KEY"

echo "MinIO initialization completed"