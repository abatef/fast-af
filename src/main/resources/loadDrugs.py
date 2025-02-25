import csv
import requests


def create_drug(row):
    # print(row)
    payload = {"name": row[0], "description": row[1]}
    response = requests.post(
        "http://4.161.48.34:8080/drugs",
        json=payload,
        headers={"Content-Type": "application/json"},
    )
    json_response = response.json()
    print(json_response)


with open("drugs_info.csv") as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=",")
    for row in csv_reader:
        create_drug(row)
