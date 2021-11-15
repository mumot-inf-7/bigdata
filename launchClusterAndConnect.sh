export REGION=europe-west4
export ZONE=${REGION}-c
export CLUSTER_NAME=bartek-bigdata-cluster
export BUCKET_NAME=bartek-bigdata-2
export PROJECT_ID=bartek-328807
gcloud beta dataproc clusters create ${CLUSTER_NAME} --enable-component-gateway --bucket ${BUCKET_NAME} --region ${REGION} --subnet default --zone ${ZONE} --master-machine-type n1-standard-4 --master-boot-disk-size 50 --num-workers 2 --worker-machine-type n1-standard-2 --worker-boot-disk-size 50 --image-version 2.0-debian10 --optional-components ZEPPELIN
gcloud beta compute ssh --zone "europe-west4-c" "bartek-bigdata-cluster-m"  --project "bartek-328807"
