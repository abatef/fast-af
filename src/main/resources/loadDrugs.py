import csv
import requests


def create_drug(row):
    payload = {"name": row[0], "description": row[1]}
    response = requests.post(
        "https://4.161.48.34:8080/drugs",  # Use HTTPS instead of HTTP
        json=payload,
        headers={"Content-Type": "application/json"},
        verify=False,
    )
    print("Status Code:", response.status_code)
    print("Response Text:", response.text)
    try:
        json_response = response.json()
        print(json_response)
    except requests.exceptions.JSONDecodeError:
        print("Invalid JSON response from server")


with open("drugs_info.csv") as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=",")
    for row in csv_reader:
        create_drug(row)
